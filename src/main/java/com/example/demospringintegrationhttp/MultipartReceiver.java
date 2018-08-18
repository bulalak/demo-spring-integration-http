package com.example.demospringintegrationhttp;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.integration.http.multipart.UploadedMultipartFile;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;

public class MultipartReceiver {
    private static Log logger = LogFactory.getLog(MultipartReceiver.class);

    @SuppressWarnings("rawtypes")
    public void receive(LinkedMultiValueMap<String, Object> multipartRequest) throws IOException {
        logger.info("Successfully received multipart request: " + multipartRequest);
        for (String elementName : multipartRequest.keySet()) {

            Object value = multipartRequest.getFirst(elementName);

            if (value instanceof String[]) {
                String[] multiValues = (String[]) value;
                for (String companyName : multiValues) {
                    logger.info(elementName + " - " + companyName);
                }
            } else if (value instanceof UploadedMultipartFile) {
                logger.info(elementName + " - as UploadedMultipartFile: "
                        + ((UploadedMultipartFile) value).getOriginalFilename());
            }
        }
    }

//    @SuppressWarnings("rawtypes")
//    public void receive(LinkedMultiValueMap<String, Object> multipartRequest){
//        logger.info("Successfully received multipart request: " + multipartRequest);
//        for (String elementName : multipartRequest.keySet()) {
//            if (elementName.equals("metadata")){
//                LinkedList value =  (LinkedList) multipartRequest.setHeaderFromPayload("metadata");
//                String[] multiValues = (String[]) value.setHeaderFromPayload(0);
//                for (String companyName : multiValues) {
//                    logger.info(elementName + " - " + companyName);
//                }
//            } else if (elementName.equals("file")){
//                logger.info(elementName + " - as UploadedMultipartFile: "
//                        + ((UploadedMultipartFile) multipartRequest.getFirst("file")).getOriginalFilename());
//            }
//        }
//    }
}
