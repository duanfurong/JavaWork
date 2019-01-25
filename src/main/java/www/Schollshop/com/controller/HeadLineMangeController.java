package www.Schollshop.com.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import www.Schollshop.com.entity.HeadLine;
import www.Schollshop.com.entity.HeadLineEntityData;
import www.Schollshop.com.entity.UploadImage;
import www.Schollshop.com.service.HeadLineService;
import www.Schollshop.com.util.HttpServletRequestUtil;
import www.Schollshop.com.util.JsonUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping(value= "HeadLineMange")
public class HeadLineMangeController {
    @Autowired
    private HeadLineService headLineService;

    @RequestMapping("/headLineMange")
    public  String  getLogin(){
        return  "headlinemange/headlinelist";
    }

    @RequestMapping("/listHeadlines")
    @ResponseBody
    public JSONObject listHeadlines(HttpServletRequest request){
        List<HeadLine> list = new ArrayList<HeadLine>();
        HeadLineEntityData headLineEntityData=new HeadLineEntityData();
        try {
            Integer enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
            Long lineId = HttpServletRequestUtil.getLong(request, "key[lineId]");
            HeadLine headLine = new HeadLine();
            long id=Long.valueOf("16");
            headLine.setLineId(id);
            headLine.setEnableStatus(1);

            list = headLineService.queryHeadLine(headLine);
            headLineEntityData.setCode(0);
            headLineEntityData.setCount(list.size());
            headLineEntityData.setData(list);
            JSONObject json= JsonUtil.getInstance().JsonToString(headLineEntityData);
            return  json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/deleteHeadLine")
    @ResponseBody
    public String deleteHeadLine(HttpServletRequest request){
        try {
            Integer lineId = HttpServletRequestUtil.getInt(request, "lineId");
             headLineService.deleteByPrimaryKey(lineId);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return  "sucess";
    }

    @RequestMapping("/editHeadLine")
    @ResponseBody
    public String editHeadLine(HttpServletRequest request){
        try {
            Long dlgheadlineId=HttpServletRequestUtil.getLong(request,"dlgheadlineId");
            String dlglineName=HttpServletRequestUtil.getString(request,"dlglineName");
            String dlgheadlink=HttpServletRequestUtil.getString(request,"dlgheadlink");
            String dlgheadstatus=HttpServletRequestUtil.getString(request,"dlgheadstatus");
            String dlgupdatetime=String.valueOf(System.currentTimeMillis()/1000);
            HeadLine headLine=new HeadLine();
            headLine.setLineId(dlgheadlineId);
            headLine.setLineName(dlglineName);
            headLine.setLineLink(dlgheadlink);
            headLine.setLastEditTime(new Date());
            headLine.setCreateTime(new Date());
            headLineService.updateByPrimaryKey(headLine);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return  "sucess";
    }

    @RequestMapping("/addHeadline")
    @ResponseBody
    public  String addHeadline(HttpServletRequest request){
        try {
            String addlineName=HttpServletRequestUtil.getString(request,"addlineName");
            String addheadlink=HttpServletRequestUtil.getString(request,"addheadlink");
            HeadLine headLine=new HeadLine();
            headLine.setLineId(Long.valueOf("123888999"));
            headLine.setLineName(addlineName);
            headLine.setLineLink(addheadlink);
            headLine.setEnableStatus(1);
            headLine.setCreateTime(new Date());
            headLine.setLastEditTime(new Date());
            headLine.setLineImg("http://wx.qlogo.cn/mmopen/c1riaxhUIm33ibwviaL3ib4mr5r5E3Gial6rpQqKAl8I4pgZicdxxibTRwkhTialgxCru2su2VmeIJKbbAIcSL6J1UWv12tgtJibBiceUo/64");
            headLineService.insert(headLine);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return  "sucess";
    }

    @RequestMapping("/uploadimg")

     public  @ResponseBody  UploadImage  uploadimg(MultipartHttpServletRequest request) throws Exception{
        String localPathDir =request.getSession().getServletContext().getRealPath("/");
        String oname="";
         if (request instanceof MultipartHttpServletRequest) {
         Iterator iter = request.getFileMap().values().iterator();
        if (iter.hasNext()) {
         MultipartFile file = (MultipartFile) iter.next();
         oname = file.getOriginalFilename();
         String   fileName= localPathDir + File.separator +oname;
         File files = new File(fileName);
         InputStream inputstream =file.getInputStream();
         byte[] bytes = new byte[1024];
         FileOutputStream outstream = new FileOutputStream(fileName);
         int index;
         while ((index = inputstream.read(bytes)) != -1) {
         outstream.write(bytes, 0, index);
         outstream.flush();
          }
         outstream.close();
         inputstream.close();
           }
         }
         UploadImage uploadImage=new UploadImage();
         uploadImage.setSuccess("导入成功");
        // uploadImage.setImgurl("../upload/"+oname);
        uploadImage.setData("hahha");
        uploadImage.setImgurl("http://wx.qlogo.cn/mmopen/c1riaxhUIm33ibwviaL3ib4mr5r5E3Gial6rpQqKAl8I4pgZicdxxibTRwkhTialgxCru2su2VmeIJKbbAIcSL6J1UWv12tgtJibBiceUo/64");
         return uploadImage;
    }

}

