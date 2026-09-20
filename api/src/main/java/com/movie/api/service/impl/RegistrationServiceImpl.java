package com.movie.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.movie.api.mapper.ActivityMapper;
import com.movie.api.mapper.RegistrationMapper;
import com.movie.api.model.entity.Activity;
import com.movie.api.model.entity.Registration;
import com.movie.api.service.RegistrationService;
import com.movie.api.utils.DataTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    private RegistrationMapper registrationMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Override
    public void create(Registration registration) throws Exception {
        //Get the selected activity ID
        String activityId = registration.getAid();
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) throw new Exception("Invalid request parameters");

        //Check whether the user is already registered
        QueryWrapper<Registration> wrapper = new QueryWrapper<>();
        wrapper.in("aid", activityId);
        wrapper.in("uid", registration.getUid());
        if (registrationMapper.selectOne(wrapper) != null) {
            throw new Exception("You have already registered for this activity");
        }

        //Check whether the activity has started
        if (DataTimeUtil.isAfterNow(activity.getStartTime())) {
            throw new Exception("This activity has not started yet");
        }
        //Check whether the activity has ended
        if (!DataTimeUtil.isAfterNow(activity.getEndTime())) {
            throw new Exception("This activity has ended");
        }
        activity.setNumber(activity.getNumber() + 1);
        activityMapper.updateById(activity);
        registration.setCreateAt(DataTimeUtil.getNowTimeString());
        registrationMapper.insert(registration);
    }

    @Override
    public Registration findById(String id) {
        return registrationMapper.selectById(id);
    }

    @Override
    public List<Registration> findAll() {
        return registrationMapper.selectList(null);
    }

    @Override
    public void deleteById(String id) {
        registrationMapper.deleteById(id);
    }

}
