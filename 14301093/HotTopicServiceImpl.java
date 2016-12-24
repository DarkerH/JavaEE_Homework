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
		TopicDAO topicDao = new TopicDAO();       //�õ������Dao
		
		List<Topic> topicList = null;     //����һ��Topic�б�

		
		List<String> topicid = null;      //����һ��topicId���б�
		
		topicid = topicDao.getAllTopic(pageIndex, numberPerPage);       //���ݴ����ҳ����ÿҳ�Ļ���������dao��ķ�����ö�Ӧ��TopicId�б�
		int length;
		Map<String, Integer> topicMap = new HashMap<String, Integer>();      //����һ��Map������KeyֵΪtopicid��valueֵΪ�����ȶ�
		int hot; // ÿ��΢�����ȶ�

		length = topicid.size();   //��ȡ���л�������

		for (int i = 0; i < length; i++) {
			hot = topicDao.getHotOfTopic(topicid
					.get(i));         //����dao�����topicid��ȡ��Ӧ�Ļ����ȶ�

			topicMap.put(topicid.get(i), hot);   //��topicid�ͻ����ȶ��Լ�ֵ�Ե���ʽ����Map

		}
        /*topicMap.put("01", 100);
        topicMap.put("02", 1);
        topicMap.put("03", 10);
        topicMap.put("04", 50);
        topicMap.put("05", 20);
        topicMap.put("06", 91);*/
		topicMap = sortByValue(topicMap);       //���ݶ���д��sortByValue�������ݻ����ȶȽ�Map��������
		
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
				Topic topic = new Topic();          //����dao��ķ�������topicid��ȡ��Ӧ�Ļ������ݺͻ��ⴴ��ʱ�䣬��һ���µĻ���
				topic.setTopic(content);
				topic.setDate(date);
				topicList.add(topic);    //���������Topic�б�
			//}
			//j++;
		}

		return topicList;   //���ظ��ݻ����ȶ������Ļ����б�
	}
	
	
	//���ݻ����ȶȽ��н���������������Map
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
