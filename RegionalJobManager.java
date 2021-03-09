import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RegionalJobManager {

	// STEP6
	// Unzip files & store to Region Y S3 bucket
	public  void unZipFilesAndStore( byte[] buffer, ZipInputStream zis)  {
									ZipEntry entry = zis.getNextEntry();
									
									try {
										
							            while (entry != null) {
							                String fileName = entry.getName();

							                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
							                int len;

							                try {
							                   
							                    while ((len = zis.read(buffer)) > 0) {
							                        outputStream.write(buffer, 0, len);
							                    }
							                } catch (IOException ex) {
							                    
							                    ex.printStackTrace();
							                }
											

											InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
							             
							                ObjectMetadata meta = new ObjectMetadata();
							               
							                meta.setContentLength(outputStream.size());
							                PutObjectRequest putObjectRequest = new PutObjectRequest(DestinationBucket , fileName, is, meta);
							                PutObjectResult putObjectResult = S3.putObject(putObjectRequest);
								}

	// STEP7
	// Create streams
	private DescribeStreamResult createStreams(AWSIot awsIot, String keyname) throws Exception {
		CreateStreamRequest createStreamRequest = new CreateStreamRequest().withStreamId(id).withFiles(files);
		return describeStreamResult;
	}

	// STEP8
	// CreateOTA
	public GetOTAUpdateResult createOTAUpdate(AWSIot awsIot, DescribeStreamResult describeStreamResult,
			List<String> targets) {
		CreateOTAUpdateRequest createOTAUpdateRequest = new CreateOTAUpdateRequest()
				.withOtaUpdateId(UUID.randomUUID().toString()).withFiles(files).withTargets(targets);
		CreateOTAUpdateResult createOTAUpdateResult = awsIot.createOTAUpdate(createOTAUpdateRequest);
		return getOTAUpdateResult;
	}

}
