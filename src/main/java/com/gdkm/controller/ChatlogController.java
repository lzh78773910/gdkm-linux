package com.gdkm.controller;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.Chatlog;
import com.gdkm.model.User;
import com.gdkm.service.ChatlogService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ChatVo;
import com.gdkm.vo.PageVo;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Api(value = "信息记录", tags = "信息记录")
@RestController
public class ChatlogController {
    @Autowired
    private ChatlogService chatlogService;
    @Autowired
    private WebSocket webSocket;

    @ApiOperation("获取聊天记录")
    @GetMapping("/byuser/chatlog/{touser}")
    public ResultVO app(@PathVariable("touser")Integer touser) {
        return ResultVOUtil.success(chatlogService.findAllByUserAndTouser(touser));
    }

    @ApiOperation("发送信息")
    @PostMapping("/byuser/chatlog/{touser}")
    public void add(@PathVariable("touser")Integer touser, @RequestParam("text")String text) {
        Subject subject = SecurityUtils.getSubject();
        User userSession = (User) subject.getPrincipal();
        ChatVo chatVo = chatlogService.addChatlog(touser, text);
        String msg="";
        String todeuser=""+touser;
        System.out.println("todeuser"+todeuser);
        JSONObject json;

        //1.将Bean转换为Json格式
        json = JSONObject.fromObject(chatVo);

        //2.将Json格式转换为String类型
        msg = json.toString();
        System.out.println("Bean转换为Json：" + msg);
        webSocket.sendOneMessage(todeuser,msg);
    }

    @ApiOperation("分页获取聊天记录")
    @GetMapping("/byuser/chatlog/{touser}/{currentPage}/{pageSize}")
    public ResultVO chatlogPage(@PathVariable("touser")Integer touser,
                                @PathVariable(value = "currentPage",required = false) Integer currentPage,
                                @PathVariable(value = "pageSize",required = false) Integer pageSize) {
        PageVo<ChatVo> pageVo = chatlogService.chatlogPage(touser, currentPage, pageSize);
        return ResultVOUtil.success(pageVo);
    }

    @ApiOperation("修改为已读")
    @PutMapping("/byuser/chatlog/{cId}")
    public ResultVO chatlogPage(@PathVariable("cId")Integer cId) {
        chatlogService.upChatlog("1",cId);
        return ResultVOUtil.success();
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("/byuser/chatlog/{cId}")
    public ResultVO deleteChatlog(@PathVariable("cId")Integer cId) {
        chatlogService.deleteChatlog(cId);
        return ResultVOUtil.success();
    }

    @ApiOperation("根据接收者id删除全部")
    @DeleteMapping("/byuser/chatlogll/{touser}")
    public ResultVO deleteChatlogAll(@PathVariable("touser")Integer touser) {
        chatlogService.deleteChatlogAll(touser);
        return ResultVOUtil.success();
    }



//    @PostMapping("/sendOneWebSocket/{userName}")
//    public void sendOneWebSocket(@PathVariable("userName") String userName,@RequestParam("text")String text) {
//        /**
//         * {
//         *   "userIocn": "http://gdkmlzh.cn-gd.ufileos.com/img%2F5e3af915-83c8-4f23-9953-25ccb46363c1.jpg?UCloudPublicKey=TOKEN_0c9d0118-e892-4784-8702-34bf5b992604&Signature=8p462HhcXyhM%2FJmhhnygrZotLjw%3D&Expires=1891772663",
//         *    "cltext": this.textarea,
//         *    "clstate": "0",
//         *    "tpye": "1"
//         *                 }
//         */
//
//
//
//        String msg="";
//        ChatVo chatVo=new ChatVo();
//        chatVo.setUserIocn("one.getUserIcon()");
//        chatVo.setCltext(text);
//        chatVo.setClstate("chatlog.getClstate()");
//        chatVo.setTpye("1");
//        //1.将Bean转换为Json格式
//
//        JSONObject json;
//
//        //1.将Bean转换为Json格式
//        json = JSONObject.fromObject(chatVo);
//
//        //2.将Json格式转换为String类型
//        msg = json.toString();
//        System.out.println("Bean转换为Json：" + msg);
//
//
//        webSocket.sendOneMessage(userName,msg);
//    }

    public static void main(String[] args) {
        ChatVo chatVo=new ChatVo();
        chatVo.setUserIocn("one.getUserIcon()");
        chatVo.setCltext("chatlog.getCltext()");
        chatVo.setClstate("chatlog.getClstate()");
        chatVo.setTpye("1");
        //1.将Bean转换为Json格式

        JSONObject json;

        //1.将Bean转换为Json格式
        json = JSONObject.fromObject(chatVo);

        //2.将Json格式转换为String类型
        String jsonStr = json.toString();
        System.out.println("Bean转换为Json：" + jsonStr);

        //3.将String转换为Json格式,Json格式通过toString()方法转换为String类型
        json = JSONObject.fromObject(jsonStr);
        System.out.println("String转换为Json：" + json.toString());

        //4.将Json格式转换为Bean
        ChatVo peo = (ChatVo) json.toBean(json, ChatVo.class);
        System.out.println("Json转换为Bean：" + peo.toString());

        //5.测试Json转换后的Bean
        System.out.println("测试Json转换后的Bean:" + peo.getCltext());
    }



}
