package yteproject.application.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yteproject.application.dtos.*;
import yteproject.application.entities.Events;
import yteproject.application.entities.Questions;
import yteproject.application.entities.Survey;
import yteproject.application.mapper.EventsMapper;
import yteproject.application.mapper.QuestionsMapper;
import yteproject.application.messages.MessageResponse;
import yteproject.application.services.*;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final PeopleService peopleService;
    private final EventService eventService;
    private final EventsMapper eventsMapper;
    private final QuestionsMapper questionsMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    private final EmailServiceImpl emailService;
    private final QuestionsService questionsService;
    private final SurveyService surveyService;

    @PostMapping("/addEvent")
    @PreAuthorize("permitAll()")
    public MessageResponse addEvent(@Valid @RequestBody EventsDto eventsDto) {
        Events event = eventsMapper.mapToEvents(eventsDto);
        return eventService.addEvent(event);
    }
    @GetMapping("/getEvents")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<EventsDto> getEvents() {
        return eventService.getEvents();
    }
    @GetMapping("/getEvents/applicantsCount")
    @PreAuthorize("permitAll()")
    public List<EventsWithApplicantsCount> getApplicantsCount() {
        return eventService.getApplicantsCount();
    }

    @GetMapping("/getEvents/{eventName}")
    @PreAuthorize("permitAll()")
    public EventsDto getEvents(@PathVariable("eventName") String eventName) {
        return eventService.getEvent(eventName);
    }

    @PutMapping("/updateEvent/{eventName}")
    @PreAuthorize("permitAll()")
    public MessageResponse updateEvent(@PathVariable String eventName,@RequestBody EventsDto eventsDto) {
        Events event=eventsMapper.mapToEvents(eventsDto);
        return eventService.updateEvent(eventName,event); // FIGURE THIS OUT
    }

    @DeleteMapping("/deleteEvent")
    @PreAuthorize("permitAll()")
    @Transactional
    public MessageResponse deleteEvent(@RequestParam String eventName) {
        return eventService.deleteEvent(eventName);
    }

    @GetMapping("/getEventsUser")
    @PreAuthorize("permitAll()")
    public List<EventsDto> getEventsUser() {
        return eventService.getEventsUser();
    }

    @PostMapping("/applyUser/{eventName}")
    @PreAuthorize("permitAll()")
    public MessageResponse applyUser(@Valid @RequestBody PeopleDto peopleDto, @PathVariable String eventName) {
        return peopleService.applyUser(peopleDto,eventName);
    }
    @GetMapping("/{eventName}/applicants")
    @PreAuthorize("permitAll()")
    public List<PeopleDto> getApplicants(@PathVariable String eventName) {
        return peopleService.getApplicants(eventName);
    }

    @RequestMapping(value = "/{eventName}/qrcode",method = RequestMethod.GET, produces = "image/png")
    @PreAuthorize("permitAll()")
    public BufferedImage generateQRCodeImage(@PathVariable String eventName,@RequestParam String name,@RequestParam String surname,@RequestParam String tcKimlikNo) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(("Name: "+name+", Surname: "+surname+", TC Kimlik No :"+tcKimlikNo+", Event Name: "+eventName), BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @GetMapping("/{eventName}/qrcodeAPI")
    @PreAuthorize("permitAll()")
    public ByteArrayOutputStream getQRCodeImage(@PathVariable String eventName, @RequestParam String name,@RequestParam String surname,@RequestParam String tcKimlikNo) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("fads", BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream;
    }


//    public void sendmail(@RequestParam String eventName, @RequestParam String name, @RequestParam String surname, @RequestParam String tcKimlikNo, @RequestParam String email) throws IOException, MessagingException {
//        emailService.sendAttachedMessage(email,"Application approved!","Thank you for applying. You can find your QR Code in the attached files",eventName,name,surname,tcKimlikNo);
//    }

    @GetMapping("/email")
    @PreAuthorize("permitAll()")
    public void sendAttachedMessage(@RequestParam String to,@RequestParam String eventName,@RequestParam String name,@RequestParam String surname,@RequestParam String tcKimlikNo) throws MessagingException, IOException,Exception {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//        BufferedImage bufferedImage = generateQRCodeImage(eventName,name,surname,tcKimlikNo);
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        helper.setTo(to);
//        helper.setSubject("Application Approved!");
//        helper.setText("Congrats!");
//        byte[] imageBytes = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
//        ByteArrayDataSource ds = new ByteArrayDataSource(imageBytes,"image/png");
//        MimeBodyPart mbp = new MimeBodyPart(ds.getInputStream());
//        mbp.setFileName("qrcode.png");
//        mbp.setDataHandler(new DataHandler(ds));
//        javaMailSender.send(msg);

//        MimeMessage mail = javaMailSender.createMimeMessage();
//        mail.setSubject("Application approved");
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setContent("<img src=\"image.jpg\"/>","text/html");
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        messageBodyPart = new MimeBodyPart();
//        BufferedImage bufferedImage = generateQRCodeImage(eventName,name,surname,tcKimlikNo);
//        byte[] imageBytes = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
//        ByteArrayDataSource ds = new ByteArrayDataSource(imageBytes,"image/jpg");
//        messageBodyPart.setDataHandler(new DataHandler(ds));
//        messageBodyPart.setFileName("image.jpg");
//        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
//        multipart.addBodyPart(messageBodyPart);
//        mail.setContent(multipart);
//        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//        helper.setTo(to);
//        helper.setSubject("Application Approved!");
//        javaMailSender.send(mail);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Application approved!");
        helper.setText("Thank you for applying!");
        BufferedImage bufferedImage = generateQRCodeImage(eventName,name,surname,tcKimlikNo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        baos.flush();
        byte[] imageBytes= baos.toByteArray();
        baos.close();
        DataSource ds = new ByteArrayDataSource(imageBytes,"image/png");
        helper.addAttachment("qrcode.png", ds);
        javaMailSender.send(message);

    }

    @PostMapping("/{eventName}/askQuestions")
    @PreAuthorize("permitAll()")
    public MessageResponse askQuestion(@PathVariable String eventName, @RequestBody QuestionsDto questions) {
        Questions question=questionsMapper.mapToQuestions(questions);
        return questionsService.askQuestion(eventName,question);
    }

    @GetMapping("/{eventName}/getQuestions")
    @PreAuthorize("permitAll()")
    public List<QuestionsDto> getQuestions(@PathVariable String eventName) {
        return questionsService.getQuestions(eventName);
    }

    @GetMapping("/{eventName}/surveyAverages")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public SurveyDto getSurveyAverages(@PathVariable String eventName) {
        return surveyService.getSurveyAverages(eventName);
    }

    @PostMapping("/{eventName}/surveyAnswer")
    @PreAuthorize("permitAll()")
    @CrossOrigin
    public MessageResponse answerSurvey(@PathVariable String eventName, @RequestBody SurveyDto surveyDto) {
        return surveyService.answerSurvey(eventName,new Survey(surveyDto.getDate(),surveyDto.getContent()));
    }


    @GetMapping("/{eventName}/giveaway")
    @PreAuthorize("permitAll()")
    @CrossOrigin
    public PeopleDto giveaway(@PathVariable String eventName) {
        return eventService.giveaway(eventName);
    }












}
