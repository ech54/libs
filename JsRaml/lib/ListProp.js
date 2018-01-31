import BaseProp from './BaseProp';

/*
  template=

  types:
    Gist:  !include types/gist.raml
    Gists: !include types/gists.raml
*/
class ListProp extends BaseProp {

  constructor(name, description, token, value) {
    super(name, description, token);
    this.propValue = value;
  }
}