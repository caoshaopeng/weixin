package com.njry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.njry.mapper.TestMapper;
import com.njry.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Autowired
	TestMapper testMapper;
	
	@Override
	public List getUserTest() {
		List testList = testMapper.getUserTest();
		if(testList != null || testList.size() <= 0) {
			return testList;
		}
		return null;
	}

}
