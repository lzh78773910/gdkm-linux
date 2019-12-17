package com.gdkm.service.impl;

import com.gdkm.Repository.TotalVisitsRepostory;
import com.gdkm.model.TotalVisits;
import com.gdkm.service.TotalVisitsService;
import com.gdkm.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Component
public class TotalVisitsServiceImpl implements TotalVisitsService {

    @Autowired
    private TotalVisitsRepostory totalVisitsRepostory;
    @Autowired
    private ServletContext servletContext;

    @Scheduled(cron = " 0 0 0 * * ?")
    public void insertPageView() {
        Set ipSet = (Set) servletContext.getAttribute("ipSet");
        int count = ipSet != null ? ipSet.size() : 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        totalVisitsRepostory.save(new TotalVisits(count,calendar.getTime()));
        servletContext.removeAttribute("ipSet");
    }


    @Override
    public long countAll() {
        long count = totalVisitsRepostory.count();
        return count;
    }
}
