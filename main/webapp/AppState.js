/*
 * AppState records state of a single object, and emits a side effect when the state is changed
 * the state is readable by readint teh appState.state attribute, which is read-only
 */
export default function AppState({init, onChange}) {
	// State is the current state of the object, it is read-only
	this.state = init;
	Object.freeze(this.state);
	this.onChange = onChange;
	this.setState = (newState) => {
		switch (typeof newState) {
			// you can pass a function which uses the previous state to create the new state
			case "function":
				this.state = newState(this.state);
				break;
			default:
				this.state = newState;
		}
		Object.freeze(this.state);
		if (this.onChange) {
			this.onChange(this.state);
		}
	}
	
}