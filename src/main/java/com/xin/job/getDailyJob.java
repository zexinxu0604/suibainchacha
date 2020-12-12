package com.xin.job;

import com.xin.dao.DailyDao;
import com.xin.model.Daily;
import com.xin.model.Factory;
import com.xin.util.HttpUtil;
import com.xin.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;

public class getDailyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Factory factory = new Factory();
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        DailyDao mapper = sqlSession.getMapper(DailyDao.class);
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("server.txt");
            if(in != null)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String strLine = null;
                int lineCount = 1;
                while(null != (strLine = bufferedReader.readLine())){
                    System.out.println("第[" + lineCount + "]行数据:[" + strLine + "]");
                    Daily daily = (Daily) factory.getObject("daily");
                    daily = HttpUtil.GetDaily(strLine);
                    mapper.insertDaily(daily);
                    lineCount++;
                }
                in.close();
                bufferedReader.close();
            } else {
                System.out.println("出错了");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        sqlSession.commit();
        sqlSession.close();

    }
}
