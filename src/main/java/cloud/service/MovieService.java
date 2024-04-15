package cloud.service;

import cloud.util.S3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cloud.model.Movie;
import cloud.repository.MovieRepository;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private String bucketName = "mybucketoaaa";

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region.static}")
    private String region;

    @Autowired
    S3Client s3Client;

    @Autowired
    DynamoDBService dynamoDBService;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public Long create(Movie movie, MultipartFile img){
        try {
            byte[] bytes = img.getBytes();  //Multipart file uploaded on server
            InputStream inputStream = new ByteArrayInputStream(bytes);
            log.info(accessKey);
            log.info(secretKey);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(img.getOriginalFilename())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
            log.info("File uploaded : {}", movie.getTitle());
            dynamoDBService.putItem("Create movie" + movie.getTitle(), LocalDateTime.now().toString());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return movieRepository.save(movie).getId();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    public void update(Movie Movie){
        movieRepository.save(Movie);
    }

    public void delete(Long id){
        movieRepository.delete(this.findById(id).orElseThrow());
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
