package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部吐槽
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 添加吐槽
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 根据ID查询吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value="/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    /**
     * 修改吐槽
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 根据ID删除吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    /**
     * 根据上级ID查询吐槽数据（分页）
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParendId(@PathVariable String parentid,
                                     @PathVariable int page,
                                     @PathVariable int size){
        Page<Spit> pageDate = spitService.findByParendId(parentid, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",new PageResult(pageDate.getTotalElements(),pageDate.getContent()));

    }

    /**
     *   吐槽点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value="/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumubup(@PathVariable String spitId){
        //判断当前用户是否已经点赞 但是现在我们没有做认证,暂时先把userId写死
            String userid="1012";
            if(redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
                return new Result(false,StatusCode.ERROR,"不能重复点赞");
            }
            spitService.thumubup(spitId);
            redisTemplate.opsForValue().set("thumbup_"+userid,1);
            return new Result(true,StatusCode.OK,"点赞成功");
    }






}
