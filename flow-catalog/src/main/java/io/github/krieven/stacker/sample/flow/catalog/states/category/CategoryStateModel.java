package io.github.krieven.stacker.sample.flow.catalog.states.category;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Stack;

public class CategoryStateModel {

    private Stack<String> path = new Stack<>();

    private String resultId;

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public Stack<String> getPath() {
        return path;
    }

    public void setPath(Stack<String> path) {
        this.path = path;
    }

    public String peek() {
        return path.peek();
    }

    public void push(String categoryId) {
        path.push(categoryId);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return path.isEmpty();
    }

    public String pop() {
        return path.pop();
    }
}
