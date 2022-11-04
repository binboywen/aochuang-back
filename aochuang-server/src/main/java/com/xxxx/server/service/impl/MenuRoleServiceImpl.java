package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuRoleMapper;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wentao
 * @since 2022-10-13
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired(required = false)
    private MenuRoleMapper menuRoleMapper;
    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @return 方法： 1、删除此角色所有菜单；2、给该角色重新加上菜单；因是两步操作，开启事务
     */
    @Override
    @Transactional // 开启事务
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        // 如果调用此接口，没传其它参数，证明是删除已有菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if (null == mids || 0 == mids.length) {
            return RespBean.success("更新成功！");
        }
        // 如果传参过来，新建批量更新方法，更新角色菜单
        Integer resule = menuRoleMapper.insertRecord(rid, mids);
        if (resule == mids.length) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
