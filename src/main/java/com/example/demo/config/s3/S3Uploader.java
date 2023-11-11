package com.example.demo.config.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3Uploader {
    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(multipartFile.getSize());

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(s3Bucket, originalFilename, multipartFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        String imagePath = amazonS3Client.getUrl(s3Bucket, originalFilename).toString(); // 접근가능한 URL 가져오기

        return imagePath;
    }

    public void deleteFile(String fileName) throws IOException {
        try{
            amazonS3Client.deleteObject(s3Bucket, fileName);
        } catch (SdkClientException e) {
            throw  new IOException("Error deleting file from S3", e);
        }
    }
}
