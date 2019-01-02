package com.liqihua.pdfdemo.controller;

import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liqihua
 * @since 2019/1/2
 */
@RestController
@RequestMapping("/pdf")
public class PDFController {
    public static final Logger LOG = LoggerFactory.getLogger(PDFController.class);


    /**
     * jvm运行临时目录里
     */
    private static String tmpPath = System.getProperty("java.io.tmpdir");

    /**
     * 把字体文件、freemaker模板文件加载到jvm运行临时目录里
     */
    static {
        try {
            ClassPathResource resourceFont = new ClassPathResource("font/SIMSUN.TTC");
            ClassPathResource resourceFtlPDF = new ClassPathResource("ftl/test.ftl");
            IOUtils.copy(resourceFont.getInputStream(), new FileOutputStream(tmpPath + "/SIMSUN.TTC"));
            IOUtils.copy(resourceFtlPDF.getInputStream(), new FileOutputStream(tmpPath + "/test.ftl"));
        } catch (IOException e) {
            LOG.error("初始化字体文件出错，无法生成合同文件", e);
        }
    }



    @RequestMapping("/download")
    public String download(HttpServletResponse response){
        /**
         * freemarker参数
         */
        Map<String, Object> param = new HashMap<>();
        param.put("username","1368888888");
        param.put("password","123456");


        Map<String, Object> service1 = new HashMap<>();
        service1.put("serviceName","服务名称1");
        service1.put("productContent","套餐内容套餐内容套餐内容套餐内容套餐内容套餐内容");
        service1.put("amount",2888);

        Map<String, Object> service2 = new HashMap<>();
        service2.put("serviceName","服务名称1");
        service2.put("productContent","套餐内容套餐内容套餐内容套餐内容套餐内容套餐内容");
        service2.put("amount",2888);


        List<Map<String, Object>> serviceList = new LinkedList<>();
        serviceList.add(service1);
        serviceList.add(service2);

        param.put("serviceList",serviceList);
        param.put("total",9888);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        try {
            OutputStream os = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition","attachment;filename="+System.currentTimeMillis()/1000+".pdf");
            response.setContentType("application/msexcel");

            cfg.setDirectoryForTemplateLoading(new File(tmpPath));
            Template template = cfg.getTemplate("/test.ftl","UTF-8");
            //准备生成pdf用的html
            StringWriter out = new StringWriter();
            template.process(param, out);
            String html = out.toString();
            //html转换pdf
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            // 解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            //字体文件放置在宋体字
            LOG.info("--- fontPath:"+tmpPath + "/SIMSUN.TTC");
            fontResolver.addFont(tmpPath + "/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
