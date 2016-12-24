package cn.edu.bjtu.weibo.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.bjtu.weibo.model.Topic;
import cn.edu.bjtu.weibo.service.*;
import cn.edu.bjtu.weibo.controller.MainPageController;
import cn.edu.bjtu.weibo.dao.*;

public class HotTopicServiceImpl implements HotTopicService {

	@Override
	public List<Topic> HotTopic(int pageIndex, int numberPerPage) {
		// TODO Auto-generated method stub
		TopicDAO topicDao = new TopicDAO();       //得到话题的Dao
		
		List<Topic> topicList = null;     //定义一个Topic列表

		
		List<String> topicid = null;      //定义一个topicId的列表
		
		topicid = topicDao.getAllTopic(pageIndex, numberPerPage);       //根据传入的页数和每页的话题数调用dao层的方法获得对应的TopicId列表
		int length;
		Map<String, Integer> topicMap = new HashMap<String, Integer>();      //创建一个Map，其中Key值为topicid，value值为话题热度
		int hot; // 每条微博的热度

		length = topicid.size();   //获取所有话题条数

		for (int i = 0; i < length; i++) {
			hot = topicDao.getHotOfTopic(topicid
					.get(i));         //调用dao层根据topicid获取对应的话题热度

			topicMap.put(topicid.get(i), hot);   //将topicid和话题热度以键值对的形式存入Map

		}
        /*topicMap.put("01", 100);
        topicMap.put("02", 1);
        topicMap.put("03", 10);
        topicMap.put("04", 50);
        topicMap.put("05", 20);
        topicMap.put("06", 91);*/
		topicMap = sortByValue(topicMap);       //根据额外写的sortByValue方法根据话题热度将Map进行排序
		
		/*for(String key : topicMap.keySet()){
			System.out.println(key+": "+topicMap.get(key));
			//System.out.println();
			System.out.println();
		}*/
		
		//int j = 1;
		
		for(String key : topicMap.keySet()){
			
			//if(j>=pageIndex && j<=pageIndex*numberPerPage){
				String content = topicDao.getContent(key);
	            String date = topicDao.getDateTopic(key);
				Topic topic = new Topic();          //调用dao层的方法根据topicid获取对应的话题内容和话题创建时间，创一个新的话题
				topic.setTopic(content);
				topic.setDate(date);
				topicList.add(topic);    //将话题存入Topic列表
			//}
			//j++;
		}

		return topicList;   //返回根据话题热度排序后的话题列表
	}
	
	
	//根据话题热度进行降序排序，重新生成Map
	public <String, Integer extends Comparable<? super Integer>> Map<String, Integer> sortByValue(
			Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/*public static void main(String[] args){
		HotTopicServiceImpl a = new HotTopicServiceImpl();
		a.HotTopic(0, 0);
		
	}*/

}
