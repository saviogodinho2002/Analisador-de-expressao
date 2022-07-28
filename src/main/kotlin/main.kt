object regexExp {
    val mulDivRegex = "(\\d+|\\d+\\.\\d+)[*/]{1}(\\d+\\.\\d+|\\d+)".toRegex();
    val addSubRegex = "(\\d+|\\d+\\.\\d+)[+-]{1}(\\d+\\.\\d+|\\d+)".toRegex();
    val openParenthesesRegex = "\\(".toRegex();
    val closeParenthesesRegex = "\\)".toRegex();
    val finalResult = "^((\\d+\\.\\d+)|(\\d+))$".toRegex();
    val numberRegex = "((\\d+\\.\\d+)|(\\d+))".toRegex();
    val addSubSymbols = "[+-]{1}".toRegex();
    val mulDivSimbols = "[*/]{1}".toRegex();


}

fun calculateMulDiv(expression: String): String {
    val match = regexExp.mulDivRegex.findAll(expression);

    val eqExpression = match.first().value;

    val matchNumber = regexExp.numberRegex.findAll(eqExpression)

    val firstNumber = matchNumber.first().value;
    val secondNumber = matchNumber.elementAt(1).value

    var result = when (regexExp.mulDivSimbols.findAll(eqExpression).first().value) {
        "*" -> (firstNumber.toDouble() * secondNumber.toDouble()).toString()
        "/" -> (firstNumber.toDouble() / secondNumber.toDouble()).toString()
        else -> "0";
    }
    return searchExpressions(expression.replace(eqExpression, result));

}

fun calculateAddSub(expression: String): String {
    val match = regexExp.addSubRegex.findAll(expression);

    val eqExpression = match.first().value;

    val matchNumber = regexExp.numberRegex.findAll(eqExpression)

    println("$eqExpression")

    val firstNumber = matchNumber.first().value;
    val secondNumber = matchNumber.elementAt(1).value

    var result = when (regexExp.addSubSymbols.findAll(eqExpression).first().value) {
        "+" -> (firstNumber.toDouble() + secondNumber.toDouble()).toString()
        "-" -> (firstNumber.toDouble() - secondNumber.toDouble()).toString()
        else -> "Expressão mal formulada";
    }


    return searchExpressions(expression.replace(eqExpression, result));
}


fun searchParentheses(expression: String): String {


    val matchOpen = regexExp.openParenthesesRegex.findAll(expression);
    val matchClose = regexExp.closeParenthesesRegex.findAll(expression);

    try {
        val open = matchOpen.first().range.first;
        val nextClose = matchClose.first().range.first;

        matchClose.count();

        println("$open $nextClose")

        var expBetween = expression.subSequence(open + 1, nextClose).toString();

        val close = regexExp.openParenthesesRegex.findAll(expBetween).count()

        expBetween = expression.subSequence(open + 1, matchClose.elementAt( close ).range.first).toString();

        val nExp = expression.replace("($expBetween)", searchExpressions(expBetween));

        return searchExpressions(nExp);
    } catch (e: Exception) {

    }

    return "Expressão mal formulada";

}

fun searchExpressions(expression: String): String {

    if(regexExp.finalResult.containsMatchIn(expression))
        return expression;
    else if (regexExp.openParenthesesRegex.containsMatchIn(expression) || regexExp.closeParenthesesRegex.containsMatchIn(expression))
        return searchParentheses(expression);

    else if (regexExp.mulDivRegex.containsMatchIn(expression))
        return calculateMulDiv(expression);

    else if (regexExp.addSubRegex.containsMatchIn(expression))
        return calculateAddSub(expression);

    return "0";
}


fun main() {


    val expression: String = "((10+6)/2)+2+1))".replace(" ","");

    println(searchExpressions(expression));

}