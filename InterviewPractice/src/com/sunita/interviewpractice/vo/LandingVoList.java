package com.sunita.interviewpractice.vo;

import java.util.ArrayList;
import java.util.List;

public class LandingVoList {
	private List<LandingVo> voList;

	public List<LandingVo> getVoList() {
		return voList;
	}

	public void AddVoList(LandingVo vo) {
		if(voList == null){
			voList = new ArrayList<LandingVo>();
		}
		
		this.voList.add(vo);
	}
	
	
	

}
