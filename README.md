### simple Hello World for DevOps trainings

### AWS
it allows to work with AWS SDK via Spring Cloud AWS bom   
https://docs.awspring.io/spring-cloud-aws/docs/3.1.0/reference/html/index.html

```
in application no need to setup AWS credentials if you run the application in AWS EC2 instance. 
Spring Cloud AWS will take care about authorization

if you want to run the app locally then firstly you need to configure local creds for AWS  
```

### controllers
  - GET returns a string
  - GET pict from resource
  - GET pict from S3 bucket
  - GET simple html page to make be able to return IMG html tag with S3 Presigned URL   
  - GET `/crash` crashes application for DevOps infrastructure purposes

### `devops` folder contains: 
- systemd units just to store them here
- cloudformation files
