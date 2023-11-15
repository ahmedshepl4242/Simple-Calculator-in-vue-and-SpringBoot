package com.example.demo.service;

//import com.example.demo.dao.PersonDao;
//import com.example.demo.model.Person;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

interface IExpressionEvaluator {


    public String infixToPostfix(String expression);

    public double evaluate(String expression);

    public int precedence(char op);

//    public boolean isOperand(char c);

    public boolean isOperator(char c);

//    public int valueOf(char c);

    public double calc(double operand1, double operand2, char op);

    public double face(String exp);

}

@Service
public class PersonService implements IExpressionEvaluator {


    //        private final PersonDao personDao;
//    @Autowired
//
//    public PersonService(@Qualifier("fakeDao")PersonDao personDao) {
//        this.personDao = personDao;
//    }


    private List<String> str = new ArrayList<>();

    public PersonService(List<String> str) {
        this.str = str;
    }

    public List<String> getStr() {
        return this.str;
    }


    @Override
    public double face(String exp) {

        str.add(exp);
        String postfix = infixToPostfix(exp);
        double res = evaluate(postfix);
        str.add(postfix);
        return res;
    }


    @Override
    public String infixToPostfix(String expression) {
        Stack<Character> s = new Stack<Character>();
        String postfix = "";
        if(expression.charAt(0) == '-') {
            expression = '0'+expression;
        }
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
//                String operand = "";
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    postfix += expression.charAt(i);
                    i++;
                }
                postfix += 'e';
                i--;
                System.out.println(c + "  " + postfix);

            } else if (c == '(') {
                s.push(c);
            } else if (c == ')') {
                while (!s.isEmpty() && s.peek() != '(') {
                    postfix += s.pop();
                }
                s.pop();
            } else {

//                System.out.println(c + " " + postfix);
                while (!s.isEmpty() && precedence(c) <= precedence(s.peek())) {
//                    System.out.println(s.size());
                    postfix += s.pop();
                }
                s.push(c);
            }
        }
        while (!s.isEmpty()) {
            postfix += s.pop();
        }
        return postfix;
    }

    @Override
    public double evaluate(String expression) {
        Stack<Double> stack2 = new Stack<Double>();
        double res = 0;
        double op1 = 0;
        double op2 = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);


            if (Character.isDigit(c)) {
                String operand = "";
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.') && expression.charAt(i) != 'e') {
                    operand += expression.charAt(i);
                    i++;
                }
                stack2.push(Double.parseDouble(operand));
            } else {
                op2 = stack2.pop();
                op1 = stack2.pop();

                stack2.push(calc(op1, op2, c));
            }
        }

        return (double) stack2.pop();
    }

    @Override
    public int precedence(char op) {

        switch (op) {

            case '*':
                return 2;
            case '/':
                return 2;
            case '+':
                return 1;
            case '-':
                return 1;
            default:
                return 0;

        }
    }


    @Override
    public boolean isOperator(char c) {
        if (c == '/' || c == '*' || c == '-' || c == '+') {
            return true;
        }
        return false;
    }


    @Override
    public double calc(double operand1, double operand2, char op) {

        switch (op) {
            case '+':
                return (operand1 + operand2);
            case '*':
                return (operand1 * operand2);
            case '/':
                return (operand1 / operand2);
            case '-':
                return (operand1 - operand2);
        }
        return 0;

    }


}
