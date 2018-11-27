package yunnex.pep.admin.upload;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import yunnex.common.config.ConfigLoader;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;

/**
 * 用户信息 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "上传文件")
@RestController
@RequestMapping("${admin_path}/file")
//@RequestMapping("/sys")
public class uploadController extends BaseController{
    @Autowired
    private ConfigLoader loader;
    
    @ApiOperation("新增用户信息")
    @PostMapping("/uploads")
    public  ResponseEntity<BizResult<String>> uploads(@RequestParam("file") MultipartFile[] files,String fileType) throws Exception {
        String filePath ="";
        String dpSavePathInfo="";
        if (files == null) {
            return result(BizResult.illegalArg("文件信息为空！")); 
        } else {
            try {
                MultipartFile upfile = files[0];
                long fileSize = upfile.getSize();
                // 上传文件大小判断
                if (fileSize > 52428800) {
                    return result(BizResult.illegalArg("文件尺寸过大！")); 
                }
                String fileName = IdWorker.getIdStr()+".jpg";
                // 创建目录
                String rootpath = loader.getProperty( Constant.File.USERFILES_BASEDIR);
                String savePath = this.getFolder(rootpath,fileType);
                // 文件存放动态目录
                filePath =rootpath+ savePath  + fileName;
                dpSavePathInfo=savePath  + fileName;
                upfile.transferTo(new File(filePath));
            } catch (Exception e) {
                throw new Exception("上传文件失败"+e.getMessage());
            }
            
        }  
        return result(BizResult.success(dpSavePathInfo));
        
    }
    
    protected String getFolder(String rootpath,String fileType) throws Exception {
        
        SimpleDateFormat formater = new SimpleDateFormat("/yyyy/MM/dd/");
        String path = "/"+"pep/" + fileType+formater.format(new Date());
       String filepath = rootpath+ path;
        File dir = new File(filepath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                throw new Exception("创建文件夹失败==" +e.getMessage());
            }
        }
        return path;
    }
    
}
