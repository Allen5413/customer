package com.zs.service.customer.impl;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityServiceImpl;
import com.zs.dao.FindPageByWhereDAO;
import com.zs.service.customer.FindCustomerByWhereService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/3/7.
 */
@Service("findCustomerByWhereService")
public class FindCustomerByWhereServiceImpl extends EntityServiceImpl implements FindCustomerByWhereService {

    @Resource
    private FindPageByWhereDAO findCustomerByWhereDAO;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> map) throws Exception {
        pageInfo = findCustomerByWhereDAO.findPageByWhere(pageInfo, map, null);
        if(null != pageInfo && null != pageInfo.getPageResults() && 0 < pageInfo.getPageResults().size()){
            List<Object[]> list = pageInfo.getPageResults();
            JSONArray jsonArray = new JSONArray();
            for(Object[] objs : list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", objs[0]);
                jsonObject.put("cName", objs[1]);
                jsonObject.put("uName", objs[2]);
                jsonObject.put("aName", objs[3]);
                jsonObject.put("address", objs[4]);
                jsonObject.put("scale", objs[5]);
                jsonObject.put("code", objs[6]);
                jsonObject.put("no", objs[7]);
                jsonObject.put("ctName", objs[8]);
                jsonObject.put("csName", objs[9]);
                jsonObject.put("lName", objs[10]);
                jsonObject.put("phone", objs[11]);
                jsonObject.put("post", objs[12]);
                jsonObject.put("remark", objs[13]);
                jsonObject.put("userId", objs[14]);
                jsonArray.add(jsonObject);
            }
            pageInfo.setPageResults(jsonArray);
        }
        return pageInfo;
    }
}
