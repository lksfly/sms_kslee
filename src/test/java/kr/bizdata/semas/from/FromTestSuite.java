package kr.bizdata.semas.from;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kr.bizdata.semas.from.cnslt.FromCnsltServiceTest;
import kr.bizdata.semas.from.dln.FromDlnServiceTest;
import kr.bizdata.semas.from.edu.FromEduServiceTest;
import kr.bizdata.semas.from.hope.FromHope1ServiceTest;
import kr.bizdata.semas.from.hope.FromHope2ServiceTest;
import kr.bizdata.semas.from.hope.FromHope3ServiceTest;
import kr.bizdata.semas.from.pln.FromPlnServiceTest;
import kr.bizdata.semas.from.smbi.VSedaLoginServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	/* cnslt */ FromCnsltServiceTest.class,
	/* dln   */ FromDlnServiceTest.class,
	/* edu   */ FromEduServiceTest.class,
	/* hope  */ FromHope1ServiceTest.class, FromHope2ServiceTest.class, FromHope3ServiceTest.class,
	/* pln   */ FromPlnServiceTest.class,
	/* smbi  */ VSedaLoginServiceTest.class
})
public class FromTestSuite {
	
}
