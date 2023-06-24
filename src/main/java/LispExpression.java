import java.util.List;
import java.util.ArrayList;
// クラス: LispExpression

public class LispExpression {
	private Object value;
	private List<LispExpression> children;

	// コンストラクタ
	public LispExpression(Object value) {
		this.value = value;
		this.children = new ArrayList<>();
	}
	// コンストラクタ
	public LispExpression() {
		this.children = new ArrayList<>();
	}
	// メソッド: getValue
	// 引数: なし
	// 戻り値: Object型
	// 処理の説明: このLispExpressionの値を返します。
	public Object getValue() {
		// 値を返す処理
		return value;
	}

	// メソッド: setValue
	// 引数: Object型のvalue
	// 戻り値: なし (void)
	// 処理の説明: このLispExpressionの値を設定します。
	public void setValue(Object value) {
		// 値を設定する処理
		this.value = value;
	}

	// メソッド: addChild
	// 引数: LispExpression型のchild
	// 戻り値: なし (void)
	// 処理の説明: このLispExpressionに子要素を追加します。
	public void addChild(LispExpression child) {
		// 子要素を追加する処理
		children.add(child);
	}

	// メソッド: getChildren
	// 引数: なし
	// 戻り値: List<LispExpression>型
	// 処理の説明: このLispExpressionの子要素のリストを返します。
	public List<LispExpression> getChildren() {
		// 子要素のリストを返す処理
		return children;
	}

	// メソッド: toString
	// 引数: なし
	// 戻り値: String型
	// 処理の説明: このLispExpressionを文字列として表現します。
	public String toString() {
		// LispExpressionを文字列に変換する処理
		return value.toString();
	}
}
