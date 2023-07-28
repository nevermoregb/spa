package com.park.spa.common.session;

import org.springframework.stereotype.Service;

import com.park.spa.common.SessionUtil;
import com.park.spa.vo.EmailCheckVo;
import com.park.spa.vo.MemberVo;

@Service
public class UserSessionService {
	
    public MemberVo getUser() throws Exception {
        return getUser(false);
    }

    public MemberVo getUser(boolean isNew) throws Exception {
    	MemberVo oldUserVo = (MemberVo) SessionUtil.getAttribute("MemberVo");

        if(oldUserVo == null && isNew) {
        	MemberVo newUserVo = new MemberVo();
            SessionUtil.setAttribute("MemberVo", newUserVo);
            
            return newUserVo;
            
        } else {
            return oldUserVo;
        }
    }

    public void removeUser() throws Exception {
        SessionUtil.removeAttribute("MemberVo");
    }
    
    /**
     * 해당 이메일이 인증된 메일인지 확인한다.
     * 
     * @param email
     * @return
     * @throws Exception
     */
    public boolean isCheckedEmail(String email) throws Exception {
    	EmailCheckVo vo = (EmailCheckVo) SessionUtil.getAttribute("emailCheckVo");
    	
    	if(vo != null && email.equals(vo.email) && vo.isChecked()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
}
