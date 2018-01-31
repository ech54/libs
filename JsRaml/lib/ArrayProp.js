import BaseProp from './BaseProp';

/*
  template=

  securedBy: [ oauth_2_0 ]
*/
class ArrayProp extends BaseProp {

  constructor(name, array, description, token) {
    super(name, description, token);
    this.propArray = array;
  }
}