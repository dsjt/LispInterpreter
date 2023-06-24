import java.util.Map;
import java.util.HashMap;

public class Environment {
    private Map<String, Object> variables; // 変数の管理
    private Map<String, Operator> operators; // 予約語に対応する処理の管理

    public Environment() {
        variables = new HashMap<>();
        operators = new HashMap<>();
        
        operators.put("+", (result, value) -> {
            if (result instanceof Double && value instanceof Double) {
                return (Double) result + (Double) value;
            } else {
                throw new LispException("加算の演算子は数値型の引数を取る必要があります。");
            }
        });

        operators.put("-", (result, value) -> {
            if (result instanceof Double && value instanceof Double) {
                return (Double) result - (Double) value;
            } else {
                throw new LispException("減算の演算子は数値型の引数を取る必要があります。");
            }
        });

        operators.put("*", (result, value) -> {
            if (result instanceof Double && value instanceof Double) {
                return (Double) result * (Double) value;
            } else {
                throw new LispException("乗算の演算子は数値型の引数を取る必要があります。");
            }
        });

        operators.put("/", (result, value) -> {
            if (result instanceof Double && value instanceof Double) {
                double divisor = (Double) value;
                if (divisor != 0) {
                    return (Double) result / divisor;
                } else {
                    throw new LispException("ゼロでの除算は許可されていません。");
                }
            } else {
                throw new LispException("除算の演算子は数値型の引数を取る必要があります。");
            }
        });
        
    }

    public boolean isDefined(String var) {
        return variables.containsKey(var);
    }

    public Object getVariableValue(String var) {
        return variables.get(var);
    }

    public void setVariableValue(String var, Object value) {
        variables.put(var, value);
    }

    public void defineOperator(String operator, Operator operation) {
        operators.put(operator, operation);
    }

    public Object applyOperator(String operator, Object result, Object value) throws LispException {
        Operator operation = operators.get(operator);
        if (operation == null) {
            throw new LispException("エラー: 未定義の予約語です。");
        }
        return operation.apply(result, value);
    }
}
