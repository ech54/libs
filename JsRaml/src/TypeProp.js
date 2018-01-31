
import BaseProp from './BaseProp';

class TypeProp extends BaseProp {

  constructor(name, values, token) {
    super(name, token);
    this.values = values;
  }
}
