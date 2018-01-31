import BaseProp from './BaseProp';
import TypeProp from './TypeProp';
import ArrayProp from './ArrayProp';
import SchemeProp from './SchemeProp';

var PropertyFactory = function(token){
    return {
      text: function(name, description){return new BaseProp(name, description, token)},
      type: function(name, values, description) {return new TypeProp(name, description, values, token)},
      array: function(name, array, description) {return new ArrayProp(name, description, array, token)},
      scheme: function(name, schemes, description) {return new SchemeProp(name, description, schemes, token)}
    }
};


export default PropertyFactory;
