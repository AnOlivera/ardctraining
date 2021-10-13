<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<template:page pageTitle="CustomerFeedBack">
    <div>
       <h3>SEND TO US YOUR FEEDBACK</h3>
    </div>

        <form:form  action="/ardctrainingstorefront/feedback/save" method="post" modelAttribute="feedbackform">
            <input  name="subject" id="subject" maxlength="50" placeholder="Subject" path="subject"/>
            <br>
            <br>
            <input  name="message" id="message" maxlength="500" placeholder="Message" path="message"/>
            <br>
            <br>
           <button type="submit" id="submit" class="btn btn-primary btn-sm">Send Feedback</button>
        </form:form>
        <br>
        <div>
            <c:forEach items="${feedback}" var="feedback" varStatus="loop">
            <table class="table table-stripped">
                <thead>
                    <th scope="col">SUBJECT</th>
                    <th scope="col">DATE</th>
                    <th scope="col">status</th>
                </thead>
                <tbody>
                    <td>${feedback.getSubject()}</td>

                    <td>${feedback.getSubmittedDate()}</td>
                    <td>${feedback.getStatus().getCode()}</td>
                </tbody>
            </table>
            </c:forEach>
        </div>

</template:page>

