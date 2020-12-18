package engine.entity;

public class Response {

    public static final Response OK_RESPONSE = new Response(true, "Congratulations, you're right!");
    public static final Response WRONG_RESPONSE = new Response(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;

    private Response(boolean result, String feedback) {
        this.success = result;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
