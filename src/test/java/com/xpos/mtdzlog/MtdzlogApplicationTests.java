package com.xpos.mtdzlog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpos.mtdzlog.meta.MetaAttribute;
import com.xpos.mtdzlog.meta.MetaData;
import com.xpos.mtdzlog.token.TokenAttribute;
import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dao.repository.TokenAttributeRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MtdzlogApplicationTests {
	
	@Autowired private TokenInfoRepository tokenInfoRepository;
	@Autowired private TokenAttributeRepository tokenAttributeRepository;

	@Test
	void contextLoads() {
		
//		try {
//			for (int i = 1; i <= 10000; i++) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				
//				Path path = Paths.get("/Users/yunhyeonjung/Documents/현중/NFT/mtdz/mtdz_" + i + ".json");
//		        // 캐릭터셋 지정
//		        Charset cs = StandardCharsets.UTF_8;
//		        //파일 내용담을 리스트
//		        List<String> list = new ArrayList<String>();
//		        StringBuffer sb = new StringBuffer();
//		        try{
//		            list = Files.readAllLines(path,cs);
//		        }catch(IOException e){
//		            e.printStackTrace();
//		        }
//		        for(String readLine : list){
//		        	sb.append(readLine);
//		        }
//		        
//		        MetaData t = objectMapper.readValue(sb.toString(), MetaData.class);   
//		        String grade = "";
//		        if (t.getDescription() != null && t.getDescription().contains("a dragon who has become a member of society. It depicts various occupations and clothing.")) {
//		        	// 그라데이션
//		        	System.out.println("그라");
//		        	grade = "gradation";
//		        } else if (t.getDescription() != null && t.getDescription().contains("a pearl hidden in a grain of sand. A dragon made of materials commonly found on the street.")) {
//		        	// 깡통
//		        	System.out.println("깡통");
//		        	grade = "canister";
//		        } else if (t.getDescription() != null && t.getDescription().contains("a flex dragon. Sensitive to fashion and full of self-confidence.")) {
//		        	// 조명
//		        	System.out.println("조명");
//		        	grade = "light";
//		        } else if (t.getDescription() != null && t.getDescription().contains(" party people at the beach. A relaxed, affluent, and delightful dragon." )) {
//		        	// 유리
//		        	System.out.println("유리");
//		        	grade = "glass";
//		        } else {
//		        	// 레전더리
//		        	System.out.println("레전더리");
//		        	grade = "legendary";
//		        }
//		        
//		        
//		        
//		        TokenInfo token = new TokenInfo();
//		        token.setDescription(t.getDescription());
//		        token.setGrade(grade);
//		        token.setImageUrl(t.getImage());
//		        token.setTokenId(i);
//		        token.setType("MTDZ");
//		        
//		        token = tokenInfoRepository.save(token);
//		        
//		        if (t.getAttributes() != null && t.getAttributes().size() > 0) {
//		        	List<TokenAttribute> tokenAttributes = new ArrayList<>();
//		        	for (MetaAttribute attr: t.getAttributes()) {
////		        		System.out.println("attr = " + attr);
//		        		TokenAttribute tokenAttribute = new TokenAttribute();
//		        		tokenAttribute.setTokenInfoId(token.getTokenId());
//		        		tokenAttribute.setAttributeKey(attr.getTrait_type());
//		        		tokenAttribute.setAttributeValue(attr.getValue());
//		        		tokenAttributes.add(tokenAttribute);
//		        	}
//		        	tokenAttributeRepository.saveAll(tokenAttributes);
//		        }
//		        
//		        System.out.println("tokenInfo = " + token);
//			}
//		} catch (Exception e) {
//			log.error("error : ", e);
//		}
	}

}
