package com.zs.service.interview.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.dao.interview.InterviewDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.interview.FindInterviewByWhereService;
import com.zs.tools.DateJsonValueProcessorTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/9.
 */
@Service("findInterviewByWhereService")
public class FindInterviewByWhereServiceImpl extends EntityServiceImpl<Interview, InterviewDAO> implements FindInterviewByWhereService {

    @Resource
    private FindPageByWhereDAO findInterviewByWhereDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> map) throws Exception {
        pageInfo = findInterviewByWhereDAO.findPageByWhere(pageInfo, map, null);
        if(null != pageInfo && null != pageInfo.getPageResults() && 0 < pageInfo.getPageResults().size()){
            List<Object[]> list = pageInfo.getPageResults();
            JSONArray jsonArray = new JSONArray();
            for(Object[] objs : list){
                //JSON日期转换必须要传bean，所以这里先把日期format后在设置值
                Interview interview = new Interview();
                interview.setOperateTime((Date) objs[9]);
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessorTools());
                JSONObject jsonObject = JSONObject.fromObject(interview, jsonConfig);
                jsonObject.put("operateTime", "null".equals(jsonObject.get("operateTime").toString()) ? "" : jsonObject.get("operateTime"));
                jsonObject.put("id", objs[0]);
                jsonObject.put("uName", objs[1]);
                jsonObject.put("cName", objs[2]);
                jsonObject.put("clName", objs[3]);
                jsonObject.put("csName", objs[4]);
                jsonObject.put("ctName", objs[5]);
                jsonObject.put("content", objs[6]);
                jsonObject.put("ip", objs[7]);
                jsonObject.put("address", objs[8]);
                jsonArray.add(jsonObject);
            }
            pageInfo.setPageResults(jsonArray);
        }
        return pageInfo;
    }
}
