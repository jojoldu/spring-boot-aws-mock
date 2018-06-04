# spring-boot-aws-mock

[![Build Status](https://travis-ci.org/jojoldu/spring-boot-aws-mock.svg?branch=master)](https://travis-ci.org/jojoldu/spring-boot-aws-mock) [![Coverage Status](https://coveralls.io/repos/github/jojoldu/spring-boot-aws-mock/badge.svg?branch=master)](https://coveralls.io/github/jojoldu/spring-boot-aws-mock?branch=master) [![Release](https://jitpack.io/v/jojoldu/spring-boot-aws-mock.svg)](https://jitpack.io/#jojoldu/spring-boot-aws-mock) 

Spring Boot Starter support for Amazon Web Service Mocking.

## Requirements

**Windows OS is not supported**

## Mock Modules

* SQS
  * Amazon Simple Queue Service

## Install

### Mock SQS

build.gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.jojoldu.spring-boot-aws-mock:mock-sqs-spring-boot-starter:0.3.2'
}
```

## Usage

### Default

application.yml

```yml
cloud:
  aws:
    region:
      static: ap-northeast-2 //aws region code (required)

sqs:
  mock:
    enabled: true
  queues:
    -
      name: 'key1' 
```

Controller.java

```java
public class SampleController {
    @Autowired private QueueMessagingTemplate messagingTemplate;

    @PostMapping("/url")
    public String save(@RequestBody RequestDto requestDto){
        String queueName = "key1";
        messagingTemplate.convertAndSend(queueName, requestDto);
        ...
    }

    @SqsListener(value = "key1")
    public void receive(String message, @Header("SenderId") String senderId) throws IOException {
        ...
    }
}
```

Run Test & Show Log

![log](./images/log.png)

### Integration Test

During integration testing, **you must specify any port** to prevent mock sqs server port conflicts.  

src/**test**/resources/application.yml or application.properteis

```yaml
sqs:
  mock:
    randomPortEnabled: true
```

This will run the server with the **new port each time** the Spring context is executed.

### Options

```yml
sqs:
  mock:
    enabled: true  //required
    randomPortEnabled: true
    host: localhost
    port: 9324
  queues:
    -
      name: 'key1-dlq'
    -
      name: 'key1'
      defaultVisibilityTimeout: 1
      delay: 0
      receiveMessageWait: 0
      deadLettersQueue:
        name: "point-dlq"
        maxReceiveCount: 1
```

* ```sqs.mock.enabled```
  * **false** (**default**, not use local mock sqs)
  * **true** (use local mock sqs) 
* ```sqs.mock.randomPortEnabled```
  * **false** (**default**, not use local mock sqs)
  * **true** (use random port) 
* ```sqs.mock.host```
  * sqs server host
  * default: localhost

* ```sqs.mock.port```
  * sqs server port
  * default: 9324
        

| AWS SQS                       | MOCK SQS                         | Default Value |
|-------------------------------|----------------------------------|---------------|
| VisibilityTimeout             | defaultVisibilityTimeout         | 30 (s)        |
| DelaySeconds                  | delay                            | 0  (s)        |
| ReceiveMessageWaitTimeSeconds | receiveMessageWait               | 0  (s)        |
| RedrivePolicy                 | deadLettersQueue                 | null          |
| RedrivePolicy.name            | deadLettersQueue.name            | null          |
| RedrivePolicy.maxReceiveCount | deadLettersQueue.maxReceiveCount | null          |

## Example

* [Basic Sample Project](https://github.com/jojoldu/spring-boot-aws-mock/tree/master/spring-boot-starter-mock-sample)
* Divided Provider Application & Consumer Application
  * [Provider (create local sqs)](https://github.com/jojoldu/spring-boot-aws-mock/blob/master/spring-boot-starter-mock-sample/src/main/resources/application.yml)
  * [Consumer (use local sqs)](https://github.com/jojoldu/spring-boot-aws-mock/blob/master/spring-boot-starter-mock-sample2/src/main/resources/application.yml)

