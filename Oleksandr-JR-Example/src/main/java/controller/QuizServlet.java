package controller;

import model.Question;
import repository.QuestionRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "QuizServlet", value = "/quiz")
public class QuizServlet extends HttpServlet {
    private static final QuestionRepository questionRepository = QuestionRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load all questions and store in session for later scoring
        List<Question> questions = questionRepository.getAll();
        req.getSession().setAttribute("questions", questions);

        // Forward to the quiz page
        getServletContext()
                .getRequestDispatcher("/quiz.jsp")
                .forward(req, resp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve questions from session
        List<Question> questions = (List<Question>) req.getSession().getAttribute("questions");


        // Prepare results for display
        req.setAttribute("score", 1);
        req.setAttribute("totalQuestions", questions.size());

        // Forward to a results JSP
        getServletContext()
                .getRequestDispatcher("/result.jsp")
                .forward(req, resp);
    }
}
