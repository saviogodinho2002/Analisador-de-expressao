object RegexExp {
    val mulDivRegex = "(\\d+|\\d+\\.\\d+)[*/]{1}(\\d+\\.\\d+|\\d+)".toRegex();
    val addSubRegex = "(\\d+|\\d+\\.\\d+)[+-]{1}(\\d+\\.\\d+|\\d+)".toRegex();
    val openParenthesesRegex = "\\(".toRegex();
    val closeParenthesesRegex = "\\)".toRegex();
    val finalResult = "^((\\d+\\.\\d+)|(\\d+))$".toRegex();
    val numberRegex = "((\\d+\\.\\d+)|(\\d+))".toRegex();
    val genericExpression = "((\\d+\\.\\d+)|(\\d+))[-+*/]((\\d+\\.\\d+)|(\\d+))".toRegex()
    val operationSimbols = "[-+*/]".toRegex();
    
}

fun calculate(expression: String): String {
    val match = RegexExp.genericExpression.findAll(expression);

    val eqExpression = match.first().value;

    val matchNumber = RegexExp.numberRegex.findAll(eqExpression)

    val firstNumber = matchNumber.first().value;
    val secondNumber = matchNumber.elementAt(1).value

    var result = when (RegexExp.operationSimbols.findAll(eqExpression).first().value) {
        "*" -> (firstNumber.toDouble() * secondNumber.toDouble()).toString()
        "/" -> (firstNumber.toDouble() / secondNumber.toDouble()).toString()
        "+" -> (firstNumber.toDouble() + secondNumber.toDouble()).toString()
        "-" -> (firstNumber.toDouble() - secondNumber.toDouble()).toString()
        else -> "0";
    }
    return searchExpressions(expression.replace(eqExpression, result));

}

fun searchParentheses(expression: String): String {


    val matchOpen = RegexExp.openParenthesesRegex.findAll(expression);
    val matchClose = RegexExp.closeParenthesesRegex.findAll(expression);
    val open = matchOpen.first().range.first;
    try {
        var nextClose:Int;
        var expBetween:String;

        var openCount:Int;

        var iterator:Int = 0;
        do{
            nextClose = matchClose.elementAt(iterator).range.first;
            expBetween = expression.subSequence(open + 1, nextClose).toString();
            openCount = RegexExp.openParenthesesRegex.findAll(expBetween).count();
            expBetween = expression.subSequence(open + 1, matchClose.elementAt( openCount ).range.first   ).toString()

        }while ( openCount != iterator++);

        val nExp = expression.replace("($expBetween)", searchExpressions(expBetween));

        return searchExpressions(nExp);
    } catch (e: Exception) {

    }

    return "Expressão mal formulada";

}

fun searchExpressions(expression: String): String {

    if(RegexExp.finalResult.containsMatchIn(expression))
        return expression;
    else if (RegexExp.openParenthesesRegex.containsMatchIn(expression) || RegexExp.closeParenthesesRegex.containsMatchIn(expression))
        return searchParentheses(expression);

    else if (RegexExp.mulDivRegex.containsMatchIn(expression))
        return calculate(expression);

    else if (RegexExp.addSubRegex.containsMatchIn(expression))
        return calculate(expression);

    return "0";
}


fun main() {


    val expression: String = "((10+62)/(2+2))".replace(" ","");

    println(searchExpressions(expression));

}