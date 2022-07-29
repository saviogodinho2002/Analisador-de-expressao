object RegexExp {
    val mulDivRegex = "(\\d+|\\d*\\.\\d+)[*/]{1}(\\d*\\.\\d+|\\d+)".toRegex();
    val addSubRegex = "(\\d+|\\d*\\.\\d+)[+-]{1}(\\d*\\.\\d+|\\d+)".toRegex();
    val openParenthesesRegex = "\\(".toRegex();
    val closeParenthesesRegex = "\\)".toRegex();
    val finalResult = "^[-]{0,1}((\\d*\\.\\d+)|(\\d+))$".toRegex();
    val numberRegex = "((\\d*\\.\\d+)|(\\d+))".toRegex();
    val genericExpression = "((\\d+\\.\\d+)|(\\d+))[-+*/]((\\d+\\.\\d+)|(\\d+))".toRegex()
    val operationSimbols = "[-+*/]".toRegex();
    val multSinal = "[-+]{2}".toRegex();
    
}

fun calculateMulDiv(expression: String): String {
    val match = RegexExp.mulDivRegex.findAll(expression);
    val eqExpression = match.first().value;

    val matchNumber = RegexExp.numberRegex.findAll(eqExpression)

    val firstNumber = matchNumber.first().value;
    val secondNumber = matchNumber.elementAt(1).value

    var result = when (RegexExp.operationSimbols.findAll(eqExpression).first().value) {
        "*" -> (firstNumber.toDouble() * secondNumber.toDouble()).toString()
        "/" -> (firstNumber.toDouble() / secondNumber.toDouble()).toString()
        else -> throw Exception("Expressão mal formulada");
    }
    return searchExpressions(expression.replace(eqExpression, result));

}
fun calculateAddSub(expression: String): String {
    val match = RegexExp.addSubRegex.findAll(expression);
    val currentExpression = match.first().value;

    val matchNumber = RegexExp.numberRegex.findAll(currentExpression)

    val firstNumber = matchNumber.first().value;
    val secondNumber = matchNumber.elementAt(1).value

    var result = when (RegexExp.operationSimbols.findAll(currentExpression).first().value) {
        "+" -> (firstNumber.toDouble() + secondNumber.toDouble()).toString()
        "-" -> (firstNumber.toDouble() - secondNumber.toDouble()).toString()
        else -> throw Exception("Expressão mal formulada");
    }
    return searchExpressions(expression.replace(currentExpression, result));

}
fun sinalGame(expression: String):String{
    val matchSinals = RegexExp.multSinal.findAll(expression);
    val sinals = matchSinals.first().value;
    val first = sinals[0];
    val second = sinals[1];
    return if(first == second)
        searchExpressions(expression.replaceFirst(sinals,"+"));
    else
        searchExpressions(expression.replaceFirst(sinals,"-"));

}

fun searchParentheses(expression: String): String {


    val matchOpen = RegexExp.openParenthesesRegex.findAll(expression);
    val matchClose = RegexExp.closeParenthesesRegex.findAll(expression);
    val open = matchOpen.first().range.first;

    var nextClose:Int;
    var expBetween:String;

    var openCount:Int;

    var iterator:Int = 0;
    do{
        nextClose = matchClose.elementAt(iterator).range.first;
        expBetween = expression.subSequence(open + 1, nextClose).toString();
        openCount = RegexExp.openParenthesesRegex.findAll(expBetween).count();

    }while ( openCount != iterator++);

    expBetween = expression.subSequence(open + 1, matchClose.elementAt( openCount ).range.first   ).toString();

    val nExp = expression.replace("($expBetween)", searchExpressions(expBetween));

    return searchExpressions(nExp);


}

fun searchExpressions(expression: String): String {
    when{
        RegexExp.multSinal.containsMatchIn(expression) -> sinalGame(expression);

        RegexExp.finalResult.containsMatchIn(expression) -> expression;

        RegexExp.openParenthesesRegex.containsMatchIn(expression) && RegexExp.closeParenthesesRegex.containsMatchIn(expression) -> searchParentheses(expression);

        RegexExp.mulDivRegex.containsMatchIn(expression)-> calculateMulDiv(expression);

        RegexExp.addSubRegex.containsMatchIn(expression) -> calculateAddSub(expression);

        else -> throw Exception("Expressão mal formulada")
    }
    throw Exception("Expressão mal formulada")
}


fun main() {

    
    val expression: String = "4--+-4".replace(" ","");

    println(searchExpressions(expression));

}