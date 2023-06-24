import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LispInterpreter {
    private LispExpression expression;
    private Object result;
    private Environment env;

    LispInterpreter(){
    	env = new Environment();
    }
    public static void main(String[] args) {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.run();
    }

    public void run() {
        StringBuilder inputBuilder = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            inputBuilder.append(line);
        }
        String input = inputBuilder.toString();
        scanner.close();

        try {
            // 入力の解析と評価処理の実装を行う
            expression = parseInput(input);
            result = evaluateExpression(expression);

            // 評価結果を表示する
            System.out.println(result);
        } catch (LispException e) {
            // エラーメッセージを表示する
            System.out.println("エラー: " + e.getMessage());
        }
    }

	 // メソッド: parseInput
	 public LispExpression parseInput(String input) throws LispException {
	     // 入力文字列をトークンに分割する
	     List<String> tokens = tokenizeInput(input);
	
	     // トークンからパースツリーを構築する
	     LispExpression parseTree = buildParseTree(tokens);
	
	     // 解析結果を返す
	     return parseTree;
	 }
	 
	 
	// メソッド: tokenizeInput
	private List<String> tokenizeInput(String input) throws LispException {
	    List<String> tokens = new ArrayList<>();
	    StringBuilder currentToken = new StringBuilder();
	    boolean withinString = false;

	    for (char c : input.toCharArray()) {
	        if (Character.isWhitespace(c) && !withinString) {
	            // 空白文字でトークンの終了を検知
	            if (currentToken.length() > 0) {
	                tokens.add(currentToken.toString());
	                currentToken.setLength(0);
	            }
	        } else if (c == '(' || c == ')') {
	            // 括弧は個別のトークンとして追加
	            if (currentToken.length() > 0) {
	                tokens.add(currentToken.toString());
	                currentToken.setLength(0);
	            }
	            tokens.add(String.valueOf(c));
	        } else if (c == '"') {
	            // 文字列の開始または終了を検知
	            if (withinString) {
	                // 文字列の終了
	                tokens.add(currentToken.toString());
	                currentToken.setLength(0);
	                withinString = false;
	            } else {
	                // 文字列の開始
	                withinString = true;
	            }
	        } else {
	            // 通常の文字をトークンとして追加
	            currentToken.append(c);
	        }
	    }

	    if (withinString) {
	        // 文字列が閉じられずに終了した場合はエラー
	        throw new LispException("エラー: 文字列が閉じられていません。");
	    }

	    // 最後のトークンを追加
	    if (currentToken.length() > 0) {
	        tokens.add(currentToken.toString());
	    }

	    return tokens;
	}
    
	// メソッド: buildParseTree
	// クラス: LispInterpreter

	public LispExpression buildParseTree(List<String> tokens) throws LispException {
	    if (tokens.isEmpty()) {
	        throw new LispException("エラー: 空の式です。");
	    }

	    String token = tokens.get(0);
	    tokens.remove(0);

		if (isNumber(token)) {
			return new LispExpression(Double.valueOf(token));
		} else if (isSymbol(token)) {
			return new LispExpression(token);
		} else if (isReservedWord(token)) {
			return new LispExpression(token);
		} else if (token.equals("(")) {
			LispExpression expression = new LispExpression();
			while (!tokens.isEmpty() && !tokens.get(0).equals(")")) {
				LispExpression childExpression = buildParseTree(tokens);
				expression.addChild(childExpression);
			}
			if (tokens.isEmpty() || !tokens.get(0).equals(")")) {
				throw new LispException("エラー: 閉じ括弧がありません。");
			}
			tokens.remove(0);
			return expression;
		} else {
			throw new LispException("エラー: 無効なトークンです。");
		}
	}

	private boolean isNumber(String token) {
	    try {
	        Double.parseDouble(token);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	private boolean isSymbol(String token) {
	    // 数値でなく、かつ特定の予約語や特殊文字でもない場合はシンボルとして評価
	    return !isNumber(token) && !isReservedWord(token) && !isSpecialCharacter(token);
	}
	
	private boolean isReservedWord(String token) {
	    // 予約語の判定ロジック
	    // 予約語であれば true を返す
	    // 予約語でなければ false を返す
	    List<String> reservedWords = Arrays.asList("+", "-", "*", "/"); // 例: "+", "-", "*", "/"
	    return reservedWords.contains(token);
	}

	private boolean isSpecialCharacter(String token) {
	    // 特殊文字の判定ロジック
	    // 特殊文字であれば true を返す
	    // 特殊文字でなければ false を返す
	    List<String> specialCharacters = Arrays.asList("(", ")"); // 例: "(", ")"
	    return specialCharacters.contains(token);
	}

	
    private Object evaluateExpression(LispExpression expression) throws LispException {
		// S式の評価処理を実装する
    	try {
    	    List<LispExpression> children = expression.getChildren();

    	    // リーフノードの場合、valueを返す
    	    if (children.isEmpty()) {
    	        return expression.getValue();
    	    }

    	    // 処理を表す要素の評価
    	    String operator = children.get(0).getValue().toString();
    	    List<LispExpression> operands = children.subList(1, children.size());

    	    // 各要素を評価して結果を計算
    	    Object result = evaluateExpression(operands.get(0));
    	    for (int i = 1; i < operands.size(); i++) {
    	        LispExpression operand = operands.get(i);
    	        Object value = evaluateExpression(operand);

    	        // 予約語に基づいて計算
    	        result = env.applyOperator(operator, result, value);
    	    }
    	    return result;
    	    
    	} catch (LispException e) {
            // 評価中にエラーが発生した場合は、LispExceptionを投げる
            throw e;    		
    	}
    }
}
