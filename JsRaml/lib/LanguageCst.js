import BaseProp from './BaseProp';
import TypeProp from './TypeProp';
import ListProp from './ListProp';
import ArrayProp from './ArrayProp';
import SchemeProp from './SchemeProp';

// Semantics
const STARTUP = '#%RAML 1.0';
const TOKEN = ':';

// General purpose
const TITLE = new BaseProp('title', 'A short, plain-text label for the API. Its value is a string.', TOKEN);
/*
const VERSION = new BaseProp('version', TOKEN);
const BASE_URI = new BaseProp('baseURI', TOKEN);
const MEDIATYPE = new TypeProp('mediaType', [APPLICATION_JSON], TOKEN);
const APPLICATION_JSON = 'application/json';
const SECURITY_SCHEMES = new ListProp('securitySchemes', [], TOKEN);

// Resources
const TYPES = new ListProp('types', [], TOKEN);
const RESOURCE_TYPES = new ListProp('resourceTypes', [], TOKEN);
const SECURED_BY = new ArrayProp('securedBy', [], TOKEN);

// Scheme
const OAUTH2_SCHEME = new SchemeProp('oauth_2_0', 'securitySchemes/oauth_2_0.raml', TOKEN);
const GIST_SCHEME = new SchemeProp('Gist', 'types/gist.raml', TOKEN);
const GISTS_SCHEME = new SchemeProp('Gists', 'types/gists.raml', TOKEN);

//Security.
*/