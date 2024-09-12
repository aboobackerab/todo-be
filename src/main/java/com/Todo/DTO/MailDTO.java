package com.Todo.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {


    private List<Message> Messages;

    @Setter
    @Getter
    public static class Message {
        private From From;
        private List<To> To;
        private String Subject;
        private String TextPart;
        private String HTMLPart;

    }

    @Setter
    @Getter
    public static class From {
        private String Email;
        private String Name;

    }

    @Setter
    @Getter
    public static class To {
        private String Email;
        private String Name;

    }
}
