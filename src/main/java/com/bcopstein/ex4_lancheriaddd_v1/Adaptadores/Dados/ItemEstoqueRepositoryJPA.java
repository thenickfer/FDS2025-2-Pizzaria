
@Repository
public class ItemEstoqueRepositoryJPA implements ItemEstoqueRepository {
    private ItemEstoqueJPARepository repo;

    @Autowired
    public ItemEstoqueRepositoryJPA (ItemEstoqueJPARepository repo){
        this.repo = repo;
    }

    @Override
    List<ItemEstoque> getAll(){
        repo.findAll().stream().map(ibd->ItemEstoqueBD.fromItemEstoqueBD(ibd)).toList();
    }

}
