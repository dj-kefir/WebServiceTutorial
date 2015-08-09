 Во все pom.xml добавляем мавен компилер плагин (появится в плагинах мавена в окне с права)
 http://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html

1) Модуль wsdlgenerator: Его предназначение создать wsdl на основе классов помеченных аннотацией @Webservice
  - Поскольку cxf для маршаллинга использует jaxb то все аргументы вебсервис функций должны быть отмечены
    аннотацией @XmlAccessorType(XmlAccessType.FIELD) или @XmlRootElement(name = "Имя")
    http://www.javacodegeeks.com/2013/06/developing-soap-web-service-using-apache-cxf.html

  - Генерация wsdl сделана при помощи cxf-java2ws-plugin и происзодит на мавен стадии <id>process-classes</id>
    к которой прикрепляется цель <goal>java2ws</goal>
  - Запуск стадии из консоли мавена: clean compiler:compile  process-classes -X (так же можно создать intellij run конфигурацию для мавена)
    ps: После clean нужно делать компиляцию, т.к. в класс файлы берутся из target директории и компилятор их заново создаст.

  http://cxf.apache.org/docs/maven-java2ws-plugin.html

  <configuration>
      <className>ru.oz.mytutorials.service.BankAccountService</className>
      <genWsdl>true</genWsdl>
      <verbose>true</verbose>
  </configuration>

2) Модуль BankAccountWebservice: Его предназначение на основе существующей wsdl при помощи cfx мавен плагина
   сгенерить вебсервис-прокси и создать ВебСервис. Поскольку прокси классы каждый раз перегенерируются то destination путь для их генерации
   нужно задавать в "target подобную" директорию проекта.
   - Генерация wsdl сделана при помощи cxf-codegen-plugin и происзодит на мавен стадии <id>process-classes</id>
   - Запуск стадии из консоли мавена: clean compiler:compile generate-sources -X (так же можно создать intellij run конфигурацию для мавена)


   Пример параметров codegen-плагина эльдорадо:
   <outputDir>/gensrc</outputDir> - переопределяет папку куда будут сгенерированы прокси (для хайбриса специально одна общая папка).
   <extraargs>
       <extraarg>-verbose</extraarg> - вывод дебаг инфы работы плагина
       <extraarg>-validate</extraarg>
       <extraarg>-wsdlLocation</extraarg>
       <extraarg>classpath:UpdateBonusOrder_SI_Sync_In.wsdl</extraarg> - откуда брать wsdl
       <extraarg>-p</extraarg> - флаг, устананвливает, что для генерируемых прокси будет переопределен пакет. Иначе прокси будут с пакетами от wsdl.
       <extraarg>net.netconomy.eld.interfaces.ws</extraarg> - имя пакета у генерируемого вебсервис-прокси.
   </extraargs>

   http://cxf.apache.org/docs/wsdl-to-java.html - опциональные параметры.

   Создание вебсервиса:
   - перетащить!!! пакет с прокси классами в папку с исходным кодом (для мавена - папка java)
   - сделать имплементацию вебсервис интерфейса
   - добавить зависимости для создания вебсвервиса:
        <artifactId>cxf-rt-frontend-jaxws</artifactId> - возможность использования jax-ws тегов cfx.
        <artifactId>cxf-rt-frontend-jaxrs</artifactId>
        <artifactId>cxf-rt-transports-http</artifactId>

   - Создать cxf-context spring контекст, прописать в нём:
      <import resource="classpath:META-INF/cxf/cxf.xml" />
      <import resource="classpath:META-INF/cxf/cxf-extension-soap.xml" />
      <import resource="classpath:META-INF/cxf/cxf-servlet.xml" />

      и бин сервиса в теге <jaxws:endpoint>

   - Что бы развернуть cxf вебсервис из под сервлет контейнера нужно отредактировать web.xml
      - загурзить спринг контексты, включая cxf-контекст:
       <context-param>
       <param-name>contextConfigLocation</param-name>
           <param-value>/WEB-INF/cxf-context.xml,/WEB-INF/applicationContext.xml</param-value>
       </context-param>
       + ПЛЮС САМ СПРИНГ-ЛИСТЕНЕР (ЗАГРУЗЧИК КОНТЕКСТОВ)

      - прописать cxf сервлет, с маппингом на урлы по которым будет реализовываться soap обмен сообщениям

   - Итоговый урл:   http://localhost:8080/soap/bankaccountservice?wsdl (например, после запуска tomcat7:run)

   Возникшие ошибки и трудности:

   В папке таргет/classes должны быть все ресурсы (xml контексты и т.д.). Ребилд через идею или compile через мавен добавит их.

   В папке таргет/generated не должно быть исходников проксей, иначе будет эксепшн о дублировании
    классов во время мавен Tomcat7:run.
    ailed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.3:compile (default-compile) on project bankaccounwebservice: Compilation failure: Compilation failure:

   Следующий эксепшн получается в результате конлфиктов версий спринга и cfx:
   cxf 2x -> spring3.x. , cxf 3x -> spring4x
    org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.apache.cxf.jaxws.EndpointImpl--1155657113': Invocation of init method failed;
