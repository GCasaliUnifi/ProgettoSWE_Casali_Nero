package sender;

public class Email {
    private String to;
    private String subject;
    private String body;

    private Email(EmailBuilder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.body = builder.body;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public static class EmailBuilder {
        private String to;
        private String subject;
        private String body;

        public EmailBuilder setTo(String to) {
            this.to = to;
            return this;
        }

        public EmailBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }
}