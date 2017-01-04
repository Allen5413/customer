package com.zs.service.customer.impl;

import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindListByWhereDAO;
import com.zs.domain.customer.Interview;
import com.zs.service.customer.FindCustomerForInterviewCountByUserIdService;
import com.zs.tools.DateJsonValueProcessorTools;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/1/3.
 */
@Service("findCustomerForInterviewCountByUserIdService")
public class FindCustomerForInterviewCountByUserIdServiceImpl extends EntityServiceImpl
        implements FindCustomerForInterviewCountByUserIdService {

    @Resource
    private FindListByWhereDAO findCustomerForInterviewCountByUserIdDAO;

    @Override
    @Transactional
    public List<JSONObject> find(Map<String, String> parmaMap) throws Exception {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        List<Object[]> list = findCustomerForInterviewCountByUserIdDAO.findListByWhere(parmaMap, null);
        if(null != list && 0 < list.size()){
            for(Object[] objs : list){
                //JSON日期转换必须要传bean，所以这里先把日期format后在设置值
                Interview interview = new Interview();
                interview.setCreateTime((Date) objs[0]);
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessorTools());
                JSONObject jsonObject = JSONObject.fromObject(interview, jsonConfig);
                jsonObject.put("operateTime", "null".equals(jsonObject.get("createTime").toString()) ? "" : jsonObject.get("createTime"));
                jsonObject.put("id", objs[1]);
                jsonObject.put("name", objs[2]);
                jsonObject.put("count", objs[3]);
                jsonList.add(jsonObject);
            }
        }
        return jsonList;
    }
}
