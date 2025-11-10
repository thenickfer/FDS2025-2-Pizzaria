public interface ItemEstoqueJPARepository extends CrudRepository <ItemEstoqueBD, Long>{
    List<ItemEstoqueBD> findAll();
    
}
