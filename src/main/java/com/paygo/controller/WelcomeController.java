
package com.paygo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paygo.entity.User;
import com.paygo.service.EventService;
import com.paygo.service.UserService;




@Controller
public class WelcomeController {

    private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

  
    
    @Autowired
    UserService userDetailsService;
   
    @Autowired
    EventService eventService;
    
    
    @RequestMapping(method = RequestMethod.GET, value = { "welcome" })
    public String showWelcomePage(Model model) {
        logger.debug("Page Request: /welcome.do");
        
        //Thread t = new Thread(new EventThread());
        //t.start();
        
        
        User user = userDetailsService.getCurrentUser();
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        return "user.welcome";
    }
    

    @RequestMapping(method = RequestMethod.GET, value = { "admin/welcome" })
    public String showAdminWelcomePage(Model model) {
        logger.debug("Page Request: /admin/welcome.do");
        User user = userDetailsService.getCurrentUser();
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        return "admin.welcome";
    }
    
    
    @RequestMapping(method=RequestMethod.GET, value="/events")
    public String getEvents(Model model){

    	model.addAttribute("events", eventService.getEvents());
		return "paygo";
    	
    }
    


    
 
    
    
    
    
    
}