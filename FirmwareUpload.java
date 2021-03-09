import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sun.tools.javac.util.Context;

public class FirmwareUpload implements RequestHandler<Map, Object> {

	/**
	 * Handler for requests to Lambda function.
	 */

	public Object handleRequest(final Map input, final Context context) {

		Map<String, String> headers = null;
		headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Custom-Header", "application/json");
		org.json.JSONObject inputJson = new org.json.JSONObject(input);

		String method = (String) inputJson.get("httpMethod");
		// Setup of POST client
		try {
			switch (method) {

			case "POST":
				try {

					// Save uploaded firmware to S3 bucket
					multipartupload(inputJson, context, s3client);
				} catch (Exception e) {

				}
				break;
			}
		} catch (Exception e) {
			return new GatewayResponse(String.format("{\"error\": \"%s\"}", e.getMessage()), headers, 500);
		}
	}

	public void multipartupload(org.json.JSONObject input, Context context, AmazonS3 s3client
			 ) throws Exception {
		try {

			// Get the uploaded file and decode from base64
			byte[] bI = Base64.getDecoder().decode(((String) input.get("body")).getBytes());

			// Get the content-type header and extract the boundary
			try {
				contentType = (String) input.getJSONObject("headers").get("Content-Type");
			} catch (Exception e) {
				contentType = (String) input.getJSONObject("headers").get("content-type");
			}
			String[] parts = contentType.toLowerCase().split(" ");
			if (parts.length != 2) {
				throw new Exception("Invalid multipart request");
			}
			String[] boundaryArray = parts[1].split("=");
			

			// Transform the boundary to a byte array
			byte[] boundary = boundaryArray[1].getBytes();
			
			
			

			// Create a ByteArrayInputStream
			ByteArrayInputStream content = new ByteArrayInputStream(bI);


			// Create a MultipartStream to process the form-data
			MultipartStream multipartStream = new MultipartStream(content, boundary, bI.length, null);

			// Create a ByteArrayOutputStream
			ByteArrayOutputStream out = null;
			
			// Find first boundary in the MultipartStream
						nextPart = multipartStream.skipPreamble();
			           // Loop through each segment
						int fileIndex = 0;
						
						while (nextPart) {
							String header = multipartStream.readHeaders();
							if (header.contains("name=\"upload\"")) {
								out = new ByteArrayOutputStream();
								multipartStream.readBodyData(out);
								InputStream is = new ByteArrayInputStream(out.toByteArray());
								upload = new OrderedJSONObject(is);
							}
							else {
								reqtarget = upload1.getJSONArray("req").getJSONObject(0).getString("target");
								if (reqtarget == null || reqtarget.isEmpty()) {
									throw new Exception("target is missing");
								}
							}

	// Store the uploads in S3 bucket
	public void Store(org.json.JSONObject input, AmazonS3 s3) throws Exception {
		PutObjectRequest putObjectRequestpmeta = new PutObjectRequest(BucketName, Key, new File("/tmp/upload.json"));
	}

	// STEP2
	// Sign the uploaded firmware and store in S3

	private void signfirmware( AmazonS3 s3, AWSsigner signerClient,
									   String codeSigningProfileName, String keyname) throws Exception {
								
							@param clientRequestToken
							//String that identifies the signing request
							@param SourceBucket
							//bucket from where the file needs to get picked 
							@param DestinationBucket
							//bucket holding the signed files
								StartSigningJobRequest startSigningJobRequest = new StartSigningJobRequest().withClientRequestToken(id)
										.withProfileName(codeSigningProfileName).withSource(SourceBucket).withDestination(DestinationBucket);
								StartSigningJobResult startSigningJobResult = signerClient.startSigningJob(startSigningJobRequest);
							}

	// STEP3
	// Download the files from S3 bucket to tmp location ,zip and upload to s3
	public void ziputil(String version, String DestinationBucket) {

		TransferManager xfer_mgr = TransferManagerBuilder.standard().withS3Client(s3).build();
		// Upload zip
		MultipleFileUpload xfer1 = xfer_mgr.uploadDirectory(DestinationBucket, key, fileName, true);
		while (!xfer1.isDone()) {
			DecimalFormat progressPercentFormat = new DecimalFormat("#.##");

		}
	}
	// STEP4
	// Enable cross region replication on DestinationBucket(RegionX) for region Y
	
	// STEP 5
	// Enable S3 event notification on Region Y 

	

}
