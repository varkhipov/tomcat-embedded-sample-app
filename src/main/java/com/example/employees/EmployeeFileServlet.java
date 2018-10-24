package com.example.employees;

import com.example.aop.aspectj.AspectJLoggingAnnotation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.Constants.*;

@WebServlet(
    name = "EmployeeFileServlet",
    urlPatterns = {"/file"}
)
@AspectJLoggingAnnotation
public class EmployeeFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("idEmployee");
        if (id != null && !id.trim().isEmpty()) {
            String action = req.getParameter("action");
            String fileType = req.getParameter("fileType");

            // Read from jar: https://stackoverflow.com/a/20389418/7464024
            // String.format: https://stackoverflow.com/a/6891209/7464024
            InputStream in = getClass().getResourceAsStream(String.format("/%s/%s.%1$s", fileType, id));

            if (in != null) {
                String mimeType;
                switch (fileType) {
                    case "html":
                        mimeType = TEXT_HTML;
                        break;
                    case "xml":
                        mimeType = APPLICATION_XML;
                        break;
                    case "json":
                        mimeType = APPLICATION_JSON;
                        break;
                    default:
                        mimeType = TEXT_PLAIN;
                }

                // gets MIME type of the file
//                String mimeType = context.getMimeType(file.getAbsolutePath());
//                if (mimeType == null) {
//                    // set to binary type if MIME mapping not found
//                    mimeType = "application/octet-stream";
//                }

                // https://stackoverflow.com/a/1756762/7464024
//                resp.setContentLength((int) file.length());

                resp.setContentType(mimeType);
                resp.setHeader(
                    "Content-Disposition",
                    "show".equals(action)
                        ? "inline"
                        : String.format("attachment; filename=\"%s.%s\"", id, fileType)
                );

                // obtains response's output stream
                OutputStream out = resp.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead = -1;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                in.close();
                out.close();
            }
        } else {
            super.doGet(req, resp);
        }
    }
}
