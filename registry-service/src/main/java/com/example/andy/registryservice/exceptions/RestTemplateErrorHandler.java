package com.example.andy.registryservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        switch (response.getStatusCode().series()){
            case SERVER_ERROR:
                try {
                    throw new Exception("Internal Server Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CLIENT_ERROR:
                String bodyMessage = getBodyMessage(response);
                StringBuilder stringBuilder = new StringBuilder(bodyMessage);
                throw new ResourceNotFoundException(stringBuilder.substring(73, 127));
        }
    }

    private String getBodyMessage (ClientHttpResponse response) throws IOException {
        InputStream inputStream = response.getBody();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];

        // read and write to buffer
        while ((nRead = inputStream.read(data, 0, data.length)) != -1){
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        //get bytes array
        byte[] bytes = buffer.toByteArray();
        String bodyMessage = new String(bytes, StandardCharsets.UTF_8);
        return bodyMessage;
    }
}
