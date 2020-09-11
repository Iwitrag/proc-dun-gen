<form:label path="resolution">Resolution</form:label>
<form:input path="resolution" cssClass="form-control"></form:input>

<form:label path="stopAfterPhase">Stop generator after</form:label>
<form:select path="stopAfterPhase" cssClass="form-control">
    <form:options/>
</form:select>

<div class="form-check">
    <form:checkbox path="drawNames" cssClass="form-check-input"></form:checkbox>
    <form:label path="drawNames" cssClass="form-check-label">Draw room names</form:label>
</div>
<div class="form-check">
    <form:checkbox path="drawConnections" cssClass="form-check-input"></form:checkbox>
    <form:label path="drawConnections" cssClass="form-check-label">Draw connections between rooms</form:label>
</div>
<div class="form-check">
    <form:checkbox path="drawCorridors" cssClass="form-check-input"></form:checkbox>
    <form:label path="drawCorridors" cssClass="form-check-label">Draw corridors</form:label>
</div>
<div class="form-check">
    <form:checkbox path="randomColors" cssClass="form-check-input"></form:checkbox>
    <form:label path="randomColors" cssClass="form-check-label">Use random colors</form:label>
</div>

