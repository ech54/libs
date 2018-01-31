
import BaseProp from './BaseProp';

class SchemeProp extends BaseProp {

  constructor(name, path, token) {
    super(name, token);
    this.path = path;
  }
}
