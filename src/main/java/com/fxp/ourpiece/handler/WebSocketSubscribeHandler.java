package com.fxp.ourpiece.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fxp.ourpiece.entity.Hero;
import com.fxp.ourpiece.service.GameService;
@Controller
public class WebSocketSubscribeHandler implements ApplicationListener<SessionSubscribeEvent> {
	@Autowired
	private GameService gameService;
	//线程安全并且可以自增自减的
	private final AtomicInteger heroIds = new AtomicInteger(0);
	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		System.out.println("onApplicationEvent:"+event);
		MessageHeaders headers = event.getMessage().getHeaders();
//		String simpSessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		String destination = SimpMessageHeaderAccessor.getDestination(headers);
		if(destination.equals("/topic/addplayer")){
			Hero hero = new Hero(heroIds.incrementAndGet(),event.getUser().getName());
			gameService.addHero(hero);
			try {
				gameService.messagingTemplate.convertAndSend("/topic/addplayer",gameService.getMapper().writeValueAsString(gameService.getHerosCollection()));
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
